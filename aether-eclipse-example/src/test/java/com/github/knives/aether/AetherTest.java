package com.github.knives.aether;

import com.google.common.io.Files;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.deployment.DeployRequest;
import org.eclipse.aether.deployment.DeploymentException;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.*;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.aether.util.artifact.SubArtifact;
import org.eclipse.aether.util.filter.DependencyFilterUtils;
import org.eclipse.aether.util.graph.manager.DependencyManagerUtils;
import org.eclipse.aether.util.graph.transformer.ConflictResolver;
import org.eclipse.aether.version.Version;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class AetherTest {

    public static final File tmpDir = Files.createTempDir();

    @Test
    public void testResolveArtifact() throws ArtifactResolutionException {
        System.out.println( "------------------------------------------------------------" );

        RepositorySystem system = Booter.newRepositorySystem();

        RepositorySystemSession session = Booter.newRepositorySystemSession( tmpDir, system );

        Artifact artifact = new DefaultArtifact( "org.eclipse.aether:aether-util:1.0.0.v20140518" );

        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact( artifact );
        artifactRequest.setRepositories( Booter.newRepositories( system, session ) );

        ArtifactResult artifactResult = system.resolveArtifact( session, artifactRequest );

        artifact = artifactResult.getArtifact();

        System.out.println( artifact + " resolved to  " + artifact.getFile() );
    }

    @Test
    public void testResolveTransitiveDependencies() throws ArtifactResolutionException, DependencyResolutionException {
        System.out.println( "------------------------------------------------------------" );

        RepositorySystem system = Booter.newRepositorySystem();

        RepositorySystemSession session = Booter.newRepositorySystemSession( tmpDir, system );

        Artifact artifact = new DefaultArtifact( "org.eclipse.aether:aether-impl:1.0.0.v20140518" );

        DependencyFilter classpathFlter = DependencyFilterUtils.classpathFilter(JavaScopes.COMPILE);

        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot( new Dependency( artifact, JavaScopes.COMPILE ) );
        collectRequest.setRepositories( Booter.newRepositories( system, session ) );

        DependencyRequest dependencyRequest = new DependencyRequest( collectRequest, classpathFlter );

        List<ArtifactResult> artifactResults =
                system.resolveDependencies( session, dependencyRequest ).getArtifactResults();

        for ( ArtifactResult artifactResult : artifactResults ) {
            System.out.println( artifactResult.getArtifact() + " resolved to " + artifactResult.getArtifact().getFile() );
        }
    }

    @Test
    public void testFindAvailableVersions() throws VersionRangeResolutionException {
        System.out.println( "------------------------------------------------------------" );

        RepositorySystem system = Booter.newRepositorySystem();

        RepositorySystemSession session = Booter.newRepositorySystemSession(  tmpDir,  system );

        Artifact artifact = new DefaultArtifact( "org.eclipse.aether:aether-util:[0,)" );

        VersionRangeRequest rangeRequest = new VersionRangeRequest();
        rangeRequest.setArtifact( artifact );
        rangeRequest.setRepositories( Booter.newRepositories( system, session ) );

        VersionRangeResult rangeResult = system.resolveVersionRange( session, rangeRequest );

        List<Version> versions = rangeResult.getVersions();

        System.out.println( "Available versions " + versions );
    }

    @Test
    @Ignore
    public void testDeployArtifacts() throws DeploymentException {
        System.out.println( "------------------------------------------------------------" );

        RepositorySystem system = Booter.newRepositorySystem();

        RepositorySystemSession session = Booter.newRepositorySystemSession( tmpDir, system );

        Artifact jarArtifact = new DefaultArtifact( "test", "org.eclipse.aether.examples", "", "jar", "0.1-SNAPSHOT" );
        jarArtifact = jarArtifact.setFile( new File( "src/main/data/demo.jar" ) );

        Artifact pomArtifact = new SubArtifact( jarArtifact, "", "pom" );
        pomArtifact = pomArtifact.setFile( new File( "pom.xml" ) );

        RemoteRepository distRepo =
                new RemoteRepository.Builder( "org.eclipse.aether.examples", "default",
                        new File( "target/dist-repo" ).toURI().toString() ).build();

        DeployRequest deployRequest = new DeployRequest();
        deployRequest.addArtifact( jarArtifact ).addArtifact( pomArtifact );
        deployRequest.setRepository( distRepo );

        system.deploy( session, deployRequest );
    }

    @Test
    public void testFindNewestVersion() throws VersionRangeResolutionException {
        System.out.println( "------------------------------------------------------------" );

        RepositorySystem system = Booter.newRepositorySystem();

        RepositorySystemSession session = Booter.newRepositorySystemSession( tmpDir, system );

        Artifact artifact = new DefaultArtifact( "org.eclipse.aether:aether-util:[0,)" );

        VersionRangeRequest rangeRequest = new VersionRangeRequest();
        rangeRequest.setArtifact( artifact );
        rangeRequest.setRepositories( Booter.newRepositories( system, session ) );

        VersionRangeResult rangeResult = system.resolveVersionRange( session, rangeRequest );

        Version newestVersion = rangeResult.getHighestVersion();

        System.out.println( "Newest version " + newestVersion + " from repository "
                + rangeResult.getRepository( newestVersion ) );
    }

    @Test
    public void testGetDependencyHierarchy() throws ArtifactDescriptorException, DependencyCollectionException {
        System.out.println( "------------------------------------------------------------" );

        RepositorySystem system = Booter.newRepositorySystem();

        DefaultRepositorySystemSession session = Booter.newRepositorySystemSession( tmpDir,  system );

        session.setConfigProperty( ConflictResolver.CONFIG_PROP_VERBOSE, true );
        session.setConfigProperty( DependencyManagerUtils.CONFIG_PROP_VERBOSE, true );

        Artifact artifact = new DefaultArtifact( "org.apache.maven:maven-aether-provider:3.1.0" );

        ArtifactDescriptorRequest descriptorRequest = new ArtifactDescriptorRequest();
        descriptorRequest.setArtifact( artifact );
        descriptorRequest.setRepositories( Booter.newRepositories( system, session ) );
        ArtifactDescriptorResult descriptorResult = system.readArtifactDescriptor( session, descriptorRequest );

        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRootArtifact( descriptorResult.getArtifact() );
        collectRequest.setDependencies( descriptorResult.getDependencies() );
        collectRequest.setManagedDependencies( descriptorResult.getManagedDependencies() );
        collectRequest.setRepositories( descriptorRequest.getRepositories() );

        CollectResult collectResult = system.collectDependencies( session, collectRequest );

        collectResult.getRoot().accept( new ConsoleDependencyGraphDumper() );
    }

    @Test
    public void testGetDependencyTree() throws DependencyCollectionException {
        System.out.println( "------------------------------------------------------------" );

        RepositorySystem system = Booter.newRepositorySystem();

        DefaultRepositorySystemSession session = Booter.newRepositorySystemSession( tmpDir,  system );

        Artifact artifact = new DefaultArtifact( "org.apache.maven:maven-aether-provider:3.1.0" );

        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot( new Dependency( artifact, "" ) );
        collectRequest.setRepositories( Booter.newRepositories( system, session ) );

        CollectResult collectResult = system.collectDependencies( session, collectRequest );

        collectResult.getRoot().accept( new ConsoleDependencyGraphDumper() );
    }

    @Test
    public void testGetDirectDependency() throws ArtifactDescriptorException {
        System.out.println( "------------------------------------------------------------" );

        RepositorySystem system = Booter.newRepositorySystem();

        DefaultRepositorySystemSession session = Booter.newRepositorySystemSession( tmpDir,  system );

        Artifact artifact = new DefaultArtifact( "org.eclipse.aether:aether-impl:1.0.0.v20140518" );

        ArtifactDescriptorRequest descriptorRequest = new ArtifactDescriptorRequest();
        descriptorRequest.setArtifact( artifact );
        descriptorRequest.setRepositories( Booter.newRepositories( system, session ) );

        ArtifactDescriptorResult descriptorResult = system.readArtifactDescriptor( session, descriptorRequest );

        for ( Dependency dependency : descriptorResult.getDependencies() ) {
            System.out.println( dependency );
        }
    }
}