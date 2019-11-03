package org.jvnet.hudson.update_center;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.lucene.document.Document;
import org.sonatype.nexus.artifact.Gav;
import org.sonatype.nexus.artifact.GavCalculator;
import org.sonatype.nexus.artifact.M2GavCalculator;
import org.sonatype.nexus.index.ArtifactContext;
import org.sonatype.nexus.index.ArtifactInfo;
import org.sonatype.nexus.index.creator.AbstractIndexCreator;

/**
 * Set the remoteUrl of artifact.
 *
 */
public class RepositoryIndexCreator extends AbstractIndexCreator {
    private static GavCalculator GAV_CALCURATOR = new M2GavCalculator();
    private URL repositoryUrl;

    public RepositoryIndexCreator(URL repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    @Override
    public void populateArtifactInfo(ArtifactContext artifactContext) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateDocument(ArtifactInfo artifactInfo, Document document) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean updateArtifactInfo(Document document, ArtifactInfo artifactInfo) {
        Gav gav = artifactInfo.calculateGav();
        String path = GAV_CALCURATOR.gavToPath(gav);
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        try {
            artifactInfo.remoteUrl = new URL(repositoryUrl, path).toString();
        } catch (MalformedURLException e) {
            return false;
        }

        return true;
    }

}
