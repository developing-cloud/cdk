package cloud.developing.iac.ec2;

import static software.amazon.awscdk.services.ec2.SubnetConfiguration.builder;
import static software.amazon.awscdk.services.ec2.SubnetType.ISOLATED;
import static software.amazon.awscdk.services.ec2.SubnetType.PRIVATE;
import static software.amazon.awscdk.services.ec2.SubnetType.PUBLIC;

import java.util.List;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.ec2.ISubnet;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcProps;

class VpcTaggedStack extends Stack {

	final Vpc vpc;

	private final String envType;

	VpcTaggedStack(Construct scope, String id, String envType) {
		super(scope, id);
		this.envType = envType;
		this.vpc = createVpc();
		changeNameTag("web", vpc.getPublicSubnets());
		changeNameTag("app", vpc.getPrivateSubnets());
		changeNameTag("db", vpc.getIsolatedSubnets());
	}

	private Vpc createVpc() {
		var subnets = List.of(subnet("web", 25, PUBLIC), subnet("app", 24, PRIVATE), subnet("db", 25, ISOLATED));
		return new Vpc(this, "vpc-" + envType,
		        VpcProps.builder().cidr("10.100.0.0/22").subnetConfiguration(subnets).build());
	}

	private SubnetConfiguration subnet(String name, int cidrMask, SubnetType subnetType) {
		return builder().name(name + "-" + envType).cidrMask(cidrMask).subnetType(subnetType).build();
	}

	private void changeNameTag(String prefix, List<ISubnet> subnets) {
		var count = 1;
		for (var subnet : subnets) {
			subnet.getNode().applyAspect(new SubnetTagAspect("Name", prefix + "-" + envType, String.valueOf(count++)));
		}
	}

}
