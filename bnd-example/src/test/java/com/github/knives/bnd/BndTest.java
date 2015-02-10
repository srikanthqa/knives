package com.github.knives.bnd;

import aQute.bnd.main.bnd;
import com.github.knives.aether.Booter;
import com.google.common.io.Files;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class BndTest {
    public static final File tmpDir = Files.createTempDir();

    @Test
    public void testPackageUse() throws Exception {

        RepositorySystem system = Booter.newRepositorySystem();

        RepositorySystemSession session = Booter.newRepositorySystemSession(tmpDir, system);

        Artifact artifact = new DefaultArtifact( "javax.activation:activation:1.1.1" );

        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact( artifact );
        artifactRequest.setRepositories( Booter.newRepositories( system, session ) );

        ArtifactResult artifactResult = system.resolveArtifact( session, artifactRequest );

        Artifact resolvedArtifact = artifactResult.getArtifact();

        final String pathToJar = resolvedArtifact.getFile().getCanonicalPath();
        System.out.println(pathToJar);

        bnd.main(new String[] { "print", "--uses", pathToJar });
    }
}
