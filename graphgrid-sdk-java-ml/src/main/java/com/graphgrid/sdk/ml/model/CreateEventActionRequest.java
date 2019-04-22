package com.graphgrid.sdk.ml.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.graphgrid.sdk.core.model.GraphGridServiceRequest;
import com.graphgrid.sdk.ml.model.event.EventActionPolicy;

@JsonAutoDetect
@NoArgsConstructor
@Getter
@Setter
public class CreateEventActionRequest extends GraphGridServiceRequest
{

    private String clusterName;
    private EventActionPolicy policy;

    public CreateEventActionRequest( String clusterName, EventActionPolicy policy )
    {
        this.clusterName = clusterName;
        this.policy = policy;
    }
}
