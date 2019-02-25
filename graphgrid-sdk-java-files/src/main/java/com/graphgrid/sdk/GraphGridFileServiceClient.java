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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GraphGridFileServiceClient extends GraphGridServiceBase implements GraphGridFileService
{

    private static final Logger LOGGER = LoggerFactory.getLogger( GraphGridFileServiceClient.class );

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
            final Map<String,List<String>> queryParams = Optional.ofNullable( request.getCustomQueryParameters() ).orElse( new HashMap<>() );
            queryParams.put( "orgGrn", Collections.singletonList( request.getOrgGrn() ) );
            request.setEndpoint( ServiceUrlBuilder.buildEndpoint( request, this.getBaseUrl(), "upload/createOnly", queryParams ) );
            request.setBody( request.getUploadFileMetadata() );
            return this.getClient().invoke( request, PersistFileNodeOnlyResponse.class, HttpMethod.POST );
        }
        catch ( IOException e )
        {
            LOGGER.error( e.getMessage() );
        }
        return new PersistFileNodeOnlyResponse().withException( new GraphGridClientException() );
    }

    public FileServiceStatusResponse status( FileServiceStatusRequest request )
    {
        try
        {
            request.setEndpoint( ServiceUrlBuilder.buildEndpoint( request, this.getBaseUrl(), "status", request.getCustomQueryParameters() ) );
            return this.getClient().invoke( request, FileServiceStatusResponse.class, HttpMethod.GET );
        }
        catch ( IOException e )
        {
            LOGGER.error( e.getMessage() );
        }
        return new FileServiceStatusResponse().withException( new GraphGridClientException() );
    }

    public void deleteFile( DeleteFileRequest request )
    {
        try
        {
            request.setEndpoint( ServiceUrlBuilder.buildEndpoint( request, this.getBaseUrl(), "delete", request.getCustomQueryParameters() ) );
            this.getClient().invoke( request, DeleteFileResponse.class, HttpMethod.DELETE );
        }
        catch ( IOException e )
        {
            LOGGER.error( e.getMessage() );
        }
    }

}
