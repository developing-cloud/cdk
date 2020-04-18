package cloud.developing.iac.ec2;

import static software.amazon.awscdk.services.ec2.InstanceClass.MEMORY5;
import static software.amazon.awscdk.services.ec2.InstanceSize.LARGE;
import static software.amazon.awscdk.services.ec2.SubnetSelection.builder;
import static software.amazon.awscdk.services.rds.ClusterParameterGroup.fromParameterGroupName;
import static software.amazon.awscdk.services.rds.DatabaseClusterEngine.AURORA_MYSQL;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.rds.DatabaseCluster;
import software.amazon.awscdk.services.rds.InstanceProps;
import software.amazon.awscdk.services.rds.Login;

class DbStack extends Stack {

	DbStack(Construct scope, String id, IVpc vpc) {
		super(scope, id);
		DatabaseCluster.Builder.create(this, "db").defaultDatabaseName("db")
		        .masterUser(Login.builder().username("julia").build()).engine(AURORA_MYSQL)
		        .parameterGroup(fromParameterGroupName(this, "paramGroup", "default.aurora-mysql5.7"))
		        .instanceProps(
		                InstanceProps.builder().vpc(vpc).vpcSubnets(builder().subnets(vpc.getIsolatedSubnets()).build())
		                        .instanceType(InstanceType.of(MEMORY5, LARGE)).build())
		        .build();
	}

}
