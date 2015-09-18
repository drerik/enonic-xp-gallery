package com.enonic.app.gallery;

import javax.servlet.http.Part;

public final class UploadPart
{
    private final Part part;

    public UploadPart( final Part part )
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
}
