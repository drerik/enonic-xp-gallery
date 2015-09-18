package com.enonic.app.gallery;

import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.enonic.xp.content.Content;
import com.enonic.xp.content.ContentPath;
import com.enonic.xp.content.ContentService;
import com.enonic.xp.content.CreateMediaParams;
import com.enonic.xp.portal.PortalRequest;
import com.enonic.xp.script.bean.BeanContext;
import com.enonic.xp.script.bean.ScriptBean;

public final class UploadSupportUsingServlet
    implements ScriptBean
{
    private Supplier<PortalRequest> portalRequest;

    private Supplier<ContentService> contentService;

    public UploadPartUsingServlet getUploadPart( final String name )
        throws Exception
    {
        final HttpServletRequest req = this.portalRequest.get().getRawRequest();
        final Part part = req.getPart( name );
        return part != null ? new UploadPartUsingServlet( part ) : null;
    }

    public String createMedia( final String parentPath, final UploadPartUsingServlet part )
        throws Exception
    {
        final CreateMediaParams params = new CreateMediaParams();
        params.parent( ContentPath.from( parentPath ) );
        params.name( part.getName() );
        params.mimeType( part.getContentType() );
        params.byteSource( part.getBytes() );

        final Content content = this.contentService.get().create( params );
        return content.getId().toString();
    }

    @Override
    public void initialize( final BeanContext context )
    {
        this.portalRequest = context.getAttribute( PortalRequest.class );
        this.contentService = context.getService( ContentService.class );
    }
}
