package cloud.developing.iac.ec2;

import software.amazon.awscdk.core.App;

public class CdkApp {

	public static void main(String[] args) {
		cfnInitEc2();
	}

	static void rawCfnEc2() {
		var app = new App();
		var ami = "ami-076431be05aaf8080";
		var instanceType = "t3.micro";
		var ec2Count = 5;
		new RawCfnEc2Stack(app, "raw-cfn-ec2", ami, instanceType, ec2Count);
		app.synth();

	}

	static void cfnEc2() {
		var app = new App();
		var ami = "ami-076431be05aaf8080";
		var instanceType = "t3.micro";
		var ec2Count = 5;
		new CfnEc2Stack(app, "cfn-ec2", ami, instanceType, ec2Count);
		app.synth();

	}

	static void ec2() {
		var app = new App();
		var ec2Count = 5;
		new Ec2Stack(app, "ec2", ec2Count);
		app.synth();

	}

	static void cfnInitEc2() {
		var app = new App();
		var ec2Count = 2;
		new CfnInitEc2Stack(app, "ec2", ec2Count);
		app.synth();

	}

	static void vpcStandard() {
		var app = new App();
		new VpcStandardStack(app, "vpc-standard");
		app.synth();
	}

	static void vpc() {
		var app = new App();
		new VpcStack(app, "vpc", "dev");
		app.synth();
	}

	static void vpcTagged() {
		var app = new App();
		new VpcTaggedStack(app, "vpc", "dev");
		app.synth();
	}

	static void vpcWithEc2AndDb() {
		var envType = "dev";
		var app = new App();
		var vpc = new VpcTaggedStack(app, "vpc", envType);
		new Ec2Stack(app, "ec2", 5, vpc.vpc);
		new DbStack(app, "db", vpc.vpc);
		app.synth();
	}

}
