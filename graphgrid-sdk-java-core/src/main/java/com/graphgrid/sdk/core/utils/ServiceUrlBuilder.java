package com.graphgrid.sdk.core.utils;


import com.graphgrid.sdk.core.model.GraphGridServiceRequest;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ServiceUrlBuilder
{

    public static URL buildEndpoint( GraphGridServiceRequest request, String baseUrl, String serviceUrl, Map<String,List<String>> queryParameters )
            throws IOException
    {
        return buildEndpoint( request, baseUrl, serviceUrl, convertQueryParamMapToString( queryParameters ) );
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
