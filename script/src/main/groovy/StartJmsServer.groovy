import org.hornetq.jms.server.config.JMSConfiguration
import org.hornetq.jms.server.config.impl.JMSConfigurationImpl
import org.hornetq.jms.server.embedded.EmbeddedJMS
import org.hornetq.core.config.Configuration
import org.hornetq.core.config.impl.ConfigurationImpl
import org.hornetq.jms.server.config.JMSQueueConfiguration
import org.hornetq.jms.server.config.impl.JMSQueueConfigurationImpl
import org.hornetq.api.config.HornetQDefaultConfiguration
import com.google.common.io.Files

final def cli = new CliBuilder(usage: 'StartFtpServer')
cli.h( longOpt: 'help', required: false, 'show usage information' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage()
	return
}

final String directory = Files.createTempDir().with { deleteOnExit(); getCanonicalPath() + '/' }


// Step 1. Create HornetQ core configuration, and set the properties accordingly
final Configuration configuration = new ConfigurationImpl()

configuration.with {
	setPersistenceEnabled(false)
	setSecurityEnabled(false)
	setPagingDirectory(directory + HornetQDefaultConfiguration.getDefaultPagingDir())
	setJournalDirectory(directory + HornetQDefaultConfiguration.getDefaultJournalDir())
	setLargeMessagesDirectory(directory + HornetQDefaultConfiguration.getDefaultLargeMessagesDir())
	setBindingsDirectory(directory + HornetQDefaultConfiguration.getDefaultBindingsDirectory())
}

// Step 2. Create the JMS configuration
final JMSConfiguration jmsConfig = new JMSConfigurationImpl()

// Step 3. Configure the JMS Queue
final JMSQueueConfiguration queueConfig = new JMSQueueConfigurationImpl("queue1", null, false, "/queue/queue1")
jmsConfig.getQueueConfigurations().add(queueConfig)

// Step 4. Start the JMS Server using the HornetQ core server and the JMS configuration
final EmbeddedJMS jmsServer = new EmbeddedJMS()
jmsServer.setConfiguration(configuration)
jmsServer.setJmsConfiguration(jmsConfig)
jmsServer.start()