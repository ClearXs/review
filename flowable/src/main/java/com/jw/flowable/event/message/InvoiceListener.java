package com.jw.flowable.event.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.el.FixedValue;

@Slf4j
@Data
public class InvoiceListener extends MessageDelegate {

    private FixedValue messageName;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("调用开票服务");
        invoke(messageName.getExpressionText());
    }
}
