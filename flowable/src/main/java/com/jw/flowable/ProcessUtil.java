package com.jw.flowable;

import lombok.NoArgsConstructor;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;

@NoArgsConstructor(staticName = "of")
public class ProcessUtil {

    public ProcessEngine getProcessEngine() {
        return ProcessEngines.getDefaultProcessEngine();
    }


}
