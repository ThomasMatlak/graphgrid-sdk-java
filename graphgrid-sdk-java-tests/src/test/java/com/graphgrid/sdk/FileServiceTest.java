package com.graphgrid.sdk;

import com.graphgrid.sdk.core.exception.GraphGridClientException;
import com.graphgrid.sdk.model.FileNode;
import com.graphgrid.sdk.model.FileServiceStatusRequest;
import com.graphgrid.sdk.model.FileServiceStatusResponse;
import com.graphgrid.sdk.model.FindFileRequest;
import com.graphgrid.sdk.model.FindFileResponse;
import com.graphgrid.sdk.model.PersistFileNodeOnlyRequest;
import com.graphgrid.sdk.model.PersistFileNodeOnlyResponse;
import com.graphgrid.sdk.model.UploadFileMetadata;
import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class FileServiceTest
{

    @Test
    public void testStatus() throws Exception
    {
        final GraphGridFileServiceClient client = new GraphGridFileServiceClient( "https://dev-api.graphgrid.com/1.0/file/" );
        final FileServiceStatusResponse response = client.status( new FileServiceStatusRequest() );

        assertNotNull( response );
    }

    @Ignore( "calls service" )
    @Test
    public void uploadGetDeleteFileInternalServerError()
    {
        final UploadFileMetadata.CreateRelationship createRelationship = new UploadFileMetadata.CreateRelationship();
        createRelationship.setGrn( "grn:gg:neo4jarchive:Mm5FzYHWZd92Tx3rqKpGHaDc0pdjMmZclyKgK4fe8sas" );
        createRelationship.setDirection( "OUTGOING" );
        createRelationship.setType( "TEST_REL" );

        // Upload files
        final FileNode fileNode = new FileNode();
        fileNode.setBucket( "sample-bucket" );
        fileNode.setFilename( "sample-file" );
        fileNode.setKey( "sample-key" );
        fileNode.setDescription( "sample" );
        fileNode.setGrn( "grn:gg:file:Mm5FzYHWZd92Tx3rqKpGHaDc0pdjMmZclyKgK4fe8sUL" );

        final UploadFileMetadata uploadFileMetadata = new UploadFileMetadata();
        uploadFileMetadata.withCreateProperties( new UploadFileMetadata.CreateProperties().withMd5( "auto" ).withSha1( "auto" ).withSha256( "auto" ) )
                .withFileNode( fileNode ).withCreateRelationships( Lists.newArrayList( createRelationship ) );
        uploadFileMetadata.setFileNode( fileNode );

        final PersistFileNodeOnlyRequest request = new PersistFileNodeOnlyRequest();
        request.setOrgGrn( "grn:gg:org:Mm5FzYHWZd92Tx3rqKpGHaDc0pdjMmZclyKgK4fe8sUL" );
        request.setUploadFileMetadata( uploadFileMetadata );

        final HashMap<String,String> map = new HashMap<>();
        map.put( "Authorization", "Bearer a2ce4bdf-7559-4d3c-8249-bed353041b8b" );
        request.withHeaders( map );


        final GraphGridFileServiceClient client = new GraphGridFileServiceClient( "https://dev-api.graphgrid.com/1.0/file/" );
        final PersistFileNodeOnlyResponse response = client.createFileNodeWithoutUploading( request );
    }

    @Test
    public void urlNotFound() throws IOException
    {
        GraphGridClientException exception = null;
        final GraphGridFileServiceClient client = new GraphGridFileServiceClient( "https://dev-api.graphgrid.com/1.0/fileNotThere/" );
        try
        {
            client.status( new FileServiceStatusRequest() );
        }
        catch ( com.graphgrid.sdk.core.exception.GraphGridClientException ex )
        {
            exception = ex;
        }
        assertNotNull( exception );
        assertEquals( exception.getHttpStatusCode(), 404 );
    }

    @Test
    public void notAuthorized() throws IOException
    {
        final FileNode fileNode = new FileNode();
        final UploadFileMetadata uploadFileMetadata = new UploadFileMetadata();
        uploadFileMetadata.setFileNode( fileNode );

        final PersistFileNodeOnlyRequest request = new PersistFileNodeOnlyRequest();
        request.setOrgGrn( "grn" );
        request.setUploadFileMetadata( uploadFileMetadata );

        final HashMap<String,String> map = new HashMap<>();
        request.withHeaders( map );


        final GraphGridFileServiceClient client = new GraphGridFileServiceClient( "https://dev-api.graphgrid.com/1.0/file/" );


        GraphGridClientException exception = null;
        try
        {
            client.createFileNodeWithoutUploading( request );
        }
        catch ( com.graphgrid.sdk.core.exception.GraphGridClientException ex )
        {
            exception = ex;
        }
        assertNotNull( exception );
        assertEquals( exception.getHttpStatusCode(), 401 );
    }

    @Ignore( "security needs to be implemented" )
    @Test
    public void notInternalServerError() throws IOException
    {
        final FileNode fileNode = new FileNode();
        fileNode.setBucket( "sample-bucket" );
        fileNode.setFilename( "sample-file" );
        fileNode.setKey( "sample-key" );
        fileNode.setDescription( "sample" );
        final UploadFileMetadata uploadFileMetadata = new UploadFileMetadata();
        uploadFileMetadata.setFileNode( fileNode );


        final PersistFileNodeOnlyRequest request = new PersistFileNodeOnlyRequest();
        request.setOrgGrn( "grn" );
        request.setUploadFileMetadata( uploadFileMetadata );

        final HashMap<String,String> map = new HashMap<>();
        map.put( "Authorization", "Bearer a2ce4bdf-7559-4d3c-8249-bed353041b8b" );
        request.withHeaders( map );


        final GraphGridFileServiceClient client = new GraphGridFileServiceClient( "https://dev-api.graphgrid.com/1.0/file/" );


        GraphGridClientException exception = null;
        try
        {
            client.createFileNodeWithoutUploading( request );
        }
        catch ( com.graphgrid.sdk.core.exception.GraphGridClientException ex )
        {
            exception = ex;
        }
        assertNotNull( exception );
        assertEquals( exception.getHttpStatusCode(), 500 );
    }

    @Test
    public void getFileNode()
    {
        String fileGrn = "grn:gg:file:Mm5FzYHWZd92Tx3rqKpGHaDc0pdjMmZclyKgK4fe8sUL";

        final GraphGridFileServiceClient client = new GraphGridFileServiceClient( "https://dev-api.graphgrid.com/1.0/file/" );

        final FindFileRequest request = new FindFileRequest();
        request.setGrn( fileGrn );
        final HashMap<String,String> map = new HashMap<>();
        map.put( "Authorization", "Bearer ddf08ff3-ee0c-4b02-86e7-1fa551a2faa7" );
        request.withHeaders( map );

        final FindFileResponse file = client.findFileByGrn( request );
        assertNotNull( file );
        assertNotNull( file.getFileNode() );
    }
}
