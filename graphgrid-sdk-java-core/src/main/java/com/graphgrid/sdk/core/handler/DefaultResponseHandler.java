package com.graphgrid.sdk.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphgrid.sdk.core.exception.GraphGridClientException;
import com.graphgrid.sdk.core.model.GraphGridServiceResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultResponseHandler<T extends GraphGridServiceResponse> implements ResponseHandler
{
    private String convertToString( HttpResponse httpResponse ) throws IOException
    {
        final BufferedReader rd = new BufferedReader( new InputStreamReader( httpResponse.getEntity().getContent() ) );
        final StringBuilder result = new StringBuilder();
        String line = "";
        while ( (line = rd.readLine()) != null )
        {
            result.append( line );
        }
        if ( StringUtils.isEmpty( result ) )
        {
            return "{}";
        }
        return result.toString();
    }

    @Override
    public GraphGridServiceResponse handle( HttpResponse httpResponse, Class responseType ) throws IOException
    {
        return handle( httpResponse, new ObjectMapper(), responseType );
    }

    @Override
    public GraphGridServiceResponse handle( HttpResponse httpResponse, ObjectMapper mapper, Class responseType ) throws IOException
    {
        if ( httpResponse.getStatusLine().getStatusCode() != 200 )
        {
            handleErrorMessage( httpResponse );
        }
        return (T) mapper.readValue( convertToString( httpResponse ), responseType );
    }

    // todo error handler need to be configurable
    private void handleErrorMessage( HttpResponse httpResponse )
    {
        String errorMessage = "";
        try
        {
            errorMessage = convertToString( httpResponse );
        }
        catch ( Exception ex )
        {
            throw new GraphGridClientException(
                    "{ 'status' :  " + httpResponse.getStatusLine().getStatusCode() + ", 'message' : 'no parseable error message'}" )
                    .withStatusCode( httpResponse.getStatusLine().getStatusCode() );
        }
        if ( StringUtils.isEmpty( errorMessage ) || errorMessage.equals( "{}" ) )
        {
            throw new GraphGridClientException(
                    "{ 'status' :  " + httpResponse.getStatusLine().getStatusCode() + ", 'message' : 'no parseable error message'}" )
                    .withStatusCode( httpResponse.getStatusLine().getStatusCode() );
        }
        throw new GraphGridClientException( errorMessage ).withStatusCode( httpResponse.getStatusLine().getStatusCode() );
    }
}
