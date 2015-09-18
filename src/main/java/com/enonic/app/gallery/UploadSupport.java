package com.enonic.app.gallery;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.common.collect.Maps;
import com.google.common.io.Files;

import com.enonic.xp.content.Content;
import com.enonic.xp.content.ContentPath;
import com.enonic.xp.content.ContentService;
import com.enonic.xp.content.CreateMediaParams;
import com.enonic.xp.portal.PortalRequest;
import com.enonic.xp.script.bean.BeanContext;
import com.enonic.xp.script.bean.ScriptBean;

public final class UploadSupport
    implements ScriptBean
{
    private DiskFileItemFactory fileItemFactory;

    private Supplier<PortalRequest> portalRequest;

    private Supplier<ContentService> contentService;

    public Map<String, FileItem> getFileItems()
        throws Exception
    {
        final HttpServletRequest req = this.portalRequest.get().getRawRequest();

        if ( !ServletFileUpload.isMultipartContent( req ) )
        {
            return Maps.newHashMap();
        }

        final ServletFileUpload upload = new ServletFileUpload( this.fileItemFactory );
        final List<FileItem> items = upload.parseRequest( req );
        final Map<String, FileItem> result = Maps.newHashMap();

        for ( final FileItem item : items )
        {
            result.put( item.getFieldName(), item );
        }

        return result;
    }

    public UploadPart getUploadPart( final String name )
        throws Exception
    {
        final Map<String, FileItem> items = getFileItems();
        final FileItem item = items.get( name );

        return item != null ? new UploadPart( item ) : null;
    }

    public String createMedia( final String parentPath, final UploadPart part )
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
        this.fileItemFactory = new DiskFileItemFactory();
        this.fileItemFactory.setRepository( Files.createTempDir() );

        this.portalRequest = context.getAttribute( PortalRequest.class );
        this.contentService = context.getService( ContentService.class );
    }
}
