package org.zerock;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageRequest {
    @Builder.Default
    private Integer page=1;
    @Builder.Default
    private Integer size=10;
    public Integer getSkip(){
        return (page-1)*size;
    }
}