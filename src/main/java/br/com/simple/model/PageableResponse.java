package br.com.simple.model;

import br.com.simple.util.CustomSortDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PageableResponse<T> extends PageImpl<T> {
    private boolean last;
    private boolean first;
    private boolean totalPages;

    public PageableResponse(@JsonProperty("content") List<T> content,
                            @JsonProperty("number") int page,
                            @JsonProperty("size") int size,
                            @JsonProperty("totalElements") int totalElements,
                            @JsonProperty("sort") @JsonDeserialize(using = CustomSortDeserializer.class) Sort sort){

        super(content, new PageRequest(page, size, sort), totalElements);
    }

    public PageableResponse(){
        super(new ArrayList<>());
    }

    @Override
    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isTotalPages() {
        return totalPages;
    }

    public void setTotalPages(boolean totalPages) {
        this.totalPages = totalPages;
    }
}
