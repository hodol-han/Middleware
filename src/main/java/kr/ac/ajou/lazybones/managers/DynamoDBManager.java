package kr.ac.ajou.lazybones.managers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDBManager {

	private static AmazonDynamoDBClient client;
	// private DynamoDB dynamoDB;
	private static DynamoDBMapper mapper;

	// private String userTableName = "User";
	// private String credentialTableName = "Credential";
	// private String groupTableName = "Group";
	// private String nodeTableName = "Node";
	// private String nodeHistoryTableName = "NodeHistory";
	// private String userHistoryTableName = "UserHistory";
	// private SimpleDateFormat dateFormatter = new
	// SimpleDateFormat("yyyy-MMdd'T'HH:mm:ss.SSS'Z'");

	/**
	 * The only information needed to create a client are security credentials
	 * consisting of the AWS Access Key ID and Secret Access Key. All other
	 * configuration, such as the service endpoints, are performed
	 * automatically. Client parameters, such as proxies, can be specified in an
	 * optional ClientConfiguration object when constructing a client.
	 *
	 * @see com.amazonaws.auth.BasicAWSCredentials
	 * @see com.amazonaws.auth.ProfilesConfigFile
	 * @see com.amazonaws.ClientConfiguration
	 */
	public static void init() {
		/*
		 * The ProfileCredentialsProvider will return your [Hong] credential
		 * profile by reading from the credentials file located at
		 * (C:\\Users\\Hong\\.aws\\credentials).
		 */

		System.setProperty("aws.accessKeyId", "AKIAI73O2GKDZQMP5JPQ");
		System.setProperty("aws.secretKey", "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");

		AWSCredentials credentials = null;
		try {
			credentials = new SystemPropertiesCredentialsProvider().getCredentials();

		} catch (Exception e) {
			throw new AmazonClientException("WTF", e);
		}
		client = new AmazonDynamoDBClient(credentials);
		Region usEast1 = Region.getRegion(Regions.US_EAST_1);
		client.setRegion(usEast1);
		// dynamoDB = new DynamoDB(client);
		mapper = new DynamoDBMapper(client);

	}

	public static DynamoDBMapper getMapper() {
		return mapper;
	}

}