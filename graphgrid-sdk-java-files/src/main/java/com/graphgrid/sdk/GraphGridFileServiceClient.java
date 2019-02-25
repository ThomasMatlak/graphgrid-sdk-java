package com.graphgrid.sdk;

import com.graphgrid.sdk.core.GraphGridHttpClient;
import com.graphgrid.sdk.core.GraphGridServiceBase;
import com.graphgrid.sdk.core.exception.GraphGridClientException;
import com.graphgrid.sdk.core.utils.HttpMethod;
import com.graphgrid.sdk.core.utils.ServiceUrlBuilder;
import com.graphgrid.sdk.model.DeleteFileRequest;
import com.graphgrid.sdk.model.DeleteFileResponse;
import com.graphgrid.sdk.model.FileServiceStatusRequest;
import com.graphgrid.sdk.model.FileServiceStatusResponse;
import com.graphgrid.sdk.model.PersistFileNodeOnlyRequest;
import com.graphgrid.sdk.model.PersistFileNodeOnlyResponse;

import java.io.IOException;

public class GraphGridFileServiceClient extends GraphGridServiceBase implements GraphGridFileService
{


    public GraphGridFileServiceClient( GraphGridHttpClient client, String baseUrl )
    {
        super( client, baseUrl );
    }

    public GraphGridFileServiceClient( String baseUrl )
    {
        super( new GraphGridHttpClient(), baseUrl );
    }

    public GraphGridFileServiceClient()
    {
        super( new GraphGridHttpClient(), null );
    }

    public PersistFileNodeOnlyResponse createFileNodeWithoutUploading( PersistFileNodeOnlyRequest request )
    {
        try
        {
            request.setEndpoint( ServiceUrlBuilder.buildEndpoint( request, this.getBaseUrl(), "upload/createOnly", request.getCustomQueryParameters() ) );
            return this.getClient().invoke( request, PersistFileNodeOnlyResponse.class, HttpMethod.POST );
        }
        // todo add logger
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return new PersistFileNodeOnlyResponse().withException( new GraphGridClientException() );
    }

    public FileServiceStatusResponse status( FileServiceStatusRequest request ) throws IOException
    {
        request.setEndpoint( ServiceUrlBuilder.buildEndpoint( request, this.getBaseUrl(), "status", request.getCustomQueryParameters() ) );
        return this.getClient().invoke( request, FileServiceStatusResponse.class, HttpMethod.GET );
    }

    public void deleteFile( DeleteFileRequest request ) throws IOException
    {
        request.setEndpoint( ServiceUrlBuilder.buildEndpoint( request, this.getBaseUrl(), "delete", request.getCustomQueryParameters() ) );
        this.getClient().invoke( request, DeleteFileResponse.class, HttpMethod.DELETE );
    }

}
