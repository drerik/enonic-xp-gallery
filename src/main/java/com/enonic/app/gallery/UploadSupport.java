package com.enonic.app.gallery;

import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.enonic.xp.content.ContentService;
import com.enonic.xp.portal.PortalRequest;
import com.enonic.xp.script.bean.BeanContext;
import com.enonic.xp.script.bean.ScriptBean;

public final class UploadSupport
    implements ScriptBean
{
    private Supplier<PortalRequest> portalRequest;

    private Supplier<ContentService> contentService;

    public UploadPart getUploadPart( final String name )
        throws Exception
    {
        final HttpServletRequest req = this.portalRequest.get().getRawRequest();
        final Part part = req.getPart( name );
        return part != null ? new UploadPart( part ) : null;
    }

    @Override
    public void initialize( final BeanContext context )
    {
        this.portalRequest = context.getAttribute( PortalRequest.class );
        this.contentService = context.getService( ContentService.class );
    }
}
