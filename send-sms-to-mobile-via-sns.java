private void testSendOtpOnPhone() {
        /* Given. */
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("mySenderID") //The sender ID shown on the device.
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                .withStringValue("0.01") //Sets the max price to 0.50 USD.
                .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Transactional") //Sets the type to promotional.
                .withDataType("String"));

        final ApplicationConfig applicationConfig = new ApplicationConfig();
        final AWSCredentials awsCredentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return applicationConfig.getAwsAccessKey(); // AWS_ACCESS_KEY
            }

            @Override
            public String getAWSSecretKey() {
                return applicationConfig.getAwsSecretKey(); // AWS_SECRET_KEY
            }
        };
        AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
                .withRegion("ap-northeast-1") /* replace with your preffered region that supports SMS */
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        String message = "My SMS message"; /*replace with custom text message */
        String phoneNumber = "+919876543210"; /* replace with target number with country code */
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        System.out.println(result);

    }
