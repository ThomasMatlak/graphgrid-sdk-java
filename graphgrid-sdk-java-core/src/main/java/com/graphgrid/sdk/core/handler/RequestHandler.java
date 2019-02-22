package com.graphgrid.sdk.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface RequestHandler
{
    String parseRequestClassToJson( ObjectMapper mapper );
}
