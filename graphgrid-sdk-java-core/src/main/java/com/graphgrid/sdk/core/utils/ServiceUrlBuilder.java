package com.graphgrid.sdk.core.utils;


import com.graphgrid.sdk.core.exception.GraphGridClientException;
import com.graphgrid.sdk.core.model.GraphGridServiceRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ServiceUrlBuilder
{

    private static final Logger LOGGER = LoggerFactory.getLogger( ServiceUrlBuilder.class );

    public static URL buildEndpoint( GraphGridServiceRequest request, String baseUrl, String serviceUrl, Map<String,List<String>> queryParameters )
    {

        IOException ex = null;
        try
        {
            return buildEndpoint( request, baseUrl, serviceUrl, convertQueryParamMapToString( queryParameters ) );
        }
        catch ( IOException e )
        {
            LOGGER.error( "error build url for request " + request.toString(), e.getMessage() );
            ex = e;
        }
        throw new GraphGridClientException( "error build url for request " + request.toString(), ex );
    }

    public static URL buildEndpoint( GraphGridServiceRequest request, String baseUrl, String serviceUrl, String queryParameters ) throws IOException
    {
        if ( request.getEndpoint() != null )
        {
            return request.getEndpoint();
        }
        else
        {
            return buildEndpoint( baseUrl, serviceUrl, queryParameters );
        }
    }

    private static String convertQueryParamMapToString( Map<String,List<String>> queryParameters )
    {
        StringBuilder queryString = new StringBuilder( "" );
        if ( queryParameters == null || queryParameters.isEmpty() )
        {
            return "";
        }
        else
        {
            queryString.append( "?" );
            for ( Map.Entry<String,List<String>> entry : queryParameters.entrySet() )
            {
                if ( entry.getValue().isEmpty() )
                {
                    continue;
                }
                else if ( entry.getValue().size() == 1 )
                {
                    queryString.append( entry.getKey() + "=" + entry.getValue().get( 0 ) );
                }
                else
                {
                    queryString.append( entry.getKey() + "=" + String.join( ",", entry.getValue() ) );
                }
                queryString.append( "&" );
            }
        }
        return StringUtils.removeEnd( queryString.toString(), "&" );
    }

    private static URL buildEndpoint( String baseUrl, String serviceUrl, String queryParameters ) throws IOException
    {
        if ( queryParameters == null || queryParameters.isEmpty() )
        {
            return new URL( baseUrl + serviceUrl );
        }
        return new URL( baseUrl + serviceUrl + queryParameters );
    }

}
