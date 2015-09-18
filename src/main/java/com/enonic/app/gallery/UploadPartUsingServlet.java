package com.enonic.app.gallery;

import javax.servlet.http.Part;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;

public final class UploadPartUsingServlet
{
    private final Part part;

    public UploadPartUsingServlet( final Part part )
    {
        this.part = part;
    }

    public String getName()
    {
        return this.part.getName();
    }

    public String getContentType()
    {
        return this.part.getContentType();
    }

    public long getSize()
    {
        return this.part.getSize();
    }

    public ByteSource getBytes()
        throws Exception
    {
        final byte[] data = ByteStreams.toByteArray( this.part.getInputStream() );
        return ByteSource.wrap( data );
    }
}
