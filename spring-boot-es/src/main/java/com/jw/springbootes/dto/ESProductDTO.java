package com.jw.springbootes.dto;

import com.jw.springbootes.FieldAnalyzer;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "product", // 索引名
        type = "product", // 类型
        shards = 1, // 默认索引分区数
        replicas = 0, // 每个分区的备份数
        refreshInterval = "-1" // 刷新间隔
)
public class ESProductDTO {

    @Id
    private Integer id;

    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String name;

    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String sellPoint;

    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String description;

    private Integer categoryId;

    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String categoryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ESProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sellPoint='" + sellPoint + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
