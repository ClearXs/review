package com.jw.springbootes.repository;

import com.jw.springbootes.dto.ESProductDTO;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.util.StringUtils;

public interface ProductRepository extends ElasticsearchRepository<ESProductDTO, Integer> {

    ESProductDTO findByName(String name);

    Page<ESProductDTO> findByNameLike(String name, Pageable pageable);

    /**
     * 复杂查询
     */
    default Page<ESProductDTO> search(Integer categoryId, String keyword, Pageable pageable) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 筛选 cid
        if (categoryId != null) {
            queryBuilder.withFilter(QueryBuilders.termQuery("categoryId", categoryId));
        }
        // 查询keyword
        if (StringUtils.hasText(keyword)) {
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = {
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("name", keyword)
,                        ScoreFunctionBuilders.weightFactorFunction(10)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("sellPoint", keyword),
                        ScoreFunctionBuilders.weightFactorFunction(5)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("categoryName", keyword),
                        ScoreFunctionBuilders.weightFactorFunction(2))
            };
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(functions)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2F);
            queryBuilder.withQuery(functionScoreQueryBuilder);
        }
        if (StringUtils.hasText(keyword)) {
            queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        } else if (pageable.getSort().isSorted()) {
            pageable.getSort().get().forEach(sortField -> queryBuilder.withSort(SortBuilders.fieldSort(sortField.getProperty())
                    .order(sortField.getDirection().isAscending() ? SortOrder.ASC : SortOrder.DESC)));
        } else {
            queryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));
        }
        // 分页
        queryBuilder.withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        return search(queryBuilder.build());
    }
}
