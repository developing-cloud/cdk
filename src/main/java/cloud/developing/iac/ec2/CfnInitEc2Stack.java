package cloud.developing.iac.ec2;

import static cloud.developing.iac.common.CdkMap.map;
import static java.lang.System.getProperty;
import static software.amazon.awscdk.services.ec2.InstanceClass.BURSTABLE3;
import static software.amazon.awscdk.services.ec2.InstanceSize.MICRO;

import java.util.List;

import software.amazon.awscdk.core.CfnResource;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.core.Tag;
import software.amazon.awscdk.services.ec2.AmazonLinuxImage;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.Instance;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.UserData;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcLookupOptions;

class CfnInitEc2Stack extends Stack {

	CfnInitEc2Stack(Construct scope, String id, int ec2Count) {
		super(scope, id, createStackProps());
		createEc2s(id, ec2Count, Vpc.fromLookup(this, "vpc", VpcLookupOptions.builder().isDefault(true).build()));
	}

	CfnInitEc2Stack(Construct scope, String id, int ec2Count, IVpc vpc) {
		super(scope, id);
		createEc2s(id, ec2Count, vpc);
	}

	private void createEc2s(String id, int ec2Count, IVpc vpc) {
		for (int i = 1; i <= ec2Count; i++) {
			var userData = UserData.forLinux();
			var ec2Id = id + "-" + i;
			var ec2 = Instance.Builder.create(this, ec2Id).instanceType(InstanceType.of(BURSTABLE3, MICRO))
			        .machineImage(new AmazonLinuxImage()).keyName("adams-eu").vpc(vpc).userData(userData).build();
			var cfnInit = "/opt/aws/bin/cfn-init -s " + getStackName() + " -r " + ec2.getInstance().getLogicalId()
			        + " --region " + getRegion();
			userData.addCommands(cfnInit);
			((CfnResource) ec2.getNode().getDefaultChild()).getCfnOptions()
			        .setMetadata(map().with("AWS::CloudFormation::Init",
			                map().with("config",
			                        map().with("packages", map().with("yum", map().with("httpd", List.of())))
			                                .with("services", map().with("sysvinit", map().with("httpd",
			                                        map().with("enabled", "true").with("ensureRunning", "true")))))));

			Tag.add(ec2, "Name", ec2Id);
		}

	}

	private static StackProps createStackProps() {
		return StackProps.builder().env(Environment.builder().region(getProperty("CDK_DEFAULT_REGION"))
		        .account(getProperty("CDK_DEFAULT_ACCOUNT")).build()).build();
	}

}
