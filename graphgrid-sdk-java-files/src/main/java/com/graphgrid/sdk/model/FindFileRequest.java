package com.graphgrid.sdk.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.graphgrid.sdk.core.model.GraphGridServiceRequest;

@JsonAutoDetect
@JsonIgnoreProperties( ignoreUnknown = true )
public class FindFileRequest extends GraphGridServiceRequest
{
    private String grn;

    public String getGrn()
    {
        return grn;
    }

    public void setGrn( String grn )
    {
        this.grn = grn;
    }
}
