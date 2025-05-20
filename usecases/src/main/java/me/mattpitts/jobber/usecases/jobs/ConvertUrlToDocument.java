package me.mattpitts.jobber.usecases.jobs;

import java.net.URI;
import me.mattpitts.jobber.util.UrlToPdfConverter;

@lombok.Builder(builderClassName = "Builder")
@lombok.Data
public class ConvertUrlToDocument {
    private final UrlToPdfConverter urlToPdfConverter;

    public byte[] execute(String url) throws Exception {
        return urlToPdfConverter.convert(URI.create(url).toURL());
    }
}
