package com.graphgrid.sdk;

import com.graphgrid.sdk.model.DeleteFileRequest;
import com.graphgrid.sdk.model.FileServiceStatusRequest;
import com.graphgrid.sdk.model.FileServiceStatusResponse;
import com.graphgrid.sdk.model.PersistFileNodeOnlyRequest;
import com.graphgrid.sdk.model.PersistFileNodeOnlyResponse;

import java.io.IOException;

public interface GraphGridFileService
{


    PersistFileNodeOnlyResponse createFileNodeWithoutUploading( PersistFileNodeOnlyRequest request );

    FileServiceStatusResponse status( FileServiceStatusRequest request ) throws IOException;

    void deleteFile( DeleteFileRequest request ) throws IOException;

}
