package com.graphgrid.sdk.core.utils;


import com.graphgrid.sdk.core.model.GraphGridServiceRequest;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ServiceUrlBuilder
{

    public static URL buildEndpoint( String baseUrl, String serviceUrl, Map<String,List<String>> queryParameters ) throws IOException
    {
        if ( queryParameters == null || queryParameters.isEmpty() )
        {
            return new URL( baseUrl + serviceUrl );
        }
        throw new NotImplementedException();
    }

    private static URL buildEndpoint( String baseUrl, String serviceUrl, String queryParameters ) throws IOException
    {
        if ( queryParameters == null || queryParameters.isEmpty() )
        {
            return new URL( baseUrl + serviceUrl );
        }
        return new URL( baseUrl + serviceUrl + queryParameters );
    }

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

    // todo needs to be implemented
    private static String convertQueryParamMapToString( Map<String,List<String>> queryParameters )
    {
        return "";
    }
}
