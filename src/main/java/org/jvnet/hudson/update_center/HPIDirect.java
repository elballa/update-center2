package org.jvnet.hudson.update_center;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.maven.artifact.resolver.AbstractArtifactResolutionException;
import org.sonatype.nexus.index.ArtifactInfo;

/**
 * HPI object linking direct to maven repository.
 */
public class HPIDirect extends HPI {

    public HPIDirect(MavenRepository repository, PluginHistory history, ArtifactInfo artifact) throws AbstractArtifactResolutionException {
        super(repository, history, artifact);
    }

    @Override
    public URL getURL() throws MalformedURLException {
        return new URL(artifact.remoteUrl);
    }
}
