package com.graphgrid.sdk.model.distributor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.graphgrid.sdk.core.model.GraphGridServiceResponse;

@JsonAutoDetect
@JsonIgnoreProperties( ignoreUnknown = true )
public class DistributorPolicyStatusResponse extends GraphGridServiceResponse
{
    private String status;

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }
}
