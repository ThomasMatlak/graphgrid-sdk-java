package com.graphgrid.sdk.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.graphgrid.sdk.core.model.GraphGridServiceResponse;

@JsonAutoDetect
@JsonIgnoreProperties( ignoreUnknown = true )
public class FindFileResponse extends GraphGridServiceResponse
{

    FileNode fileNode;

    public FileNode getFileNode()
    {
        return fileNode;
    }

    public void setFileNode( FileNode fileNode )
    {
        this.fileNode = fileNode;
    }
}
