

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Build information</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- Fraud Detector -->
## Fraud Detector

This project provided Rule based fraud detection of baking transaction. As input this module accepts a source file in CSV format. The source file should have eight columns in below mentioned sequence:
`transactionId, timestamp, sourceId, toAccount, fromAccount, statusCode, currency, amount`
If there is a header column in the source file it will be ignored. The output will replace the source file and produce output in CSV format with the source data and adding one column to the data with an additional column `i.e. validity = valid or fraud`. A new header will be added to the file:
`transactionId, timestamp, sourceId, toAccount, fromAccount, statusCode, currency, amount, validity`

The module has validations on the input data and throw custom exception when mismatch configuration, invalid argument, invalid data, etc.

Fraud detection is done based on rules. The module has a configurable Rule book which defines the priority as well as the list of rules. Rule implemented in the system are as follows:

- Fraud Account list
	- This rule is based on a list of fraud account list, detected in historical transactions.
- Country Change
	- If the Country changes between two transaction in a very short period of time (time is configuration) the transaction is classified as fraud
- Non Human Transactions
	- If the frequency of transaction is very high (not possible for Humans), the transactions are classified as fraud. The min time between transaction is configurable.
- Invalid Status Codes in multiple transactions
	- If the transactions are having multiple invalid status code transactions like: (Daily limit exceeded, Incorrect pin, Insufficient balance) the transactions are classified as fraud. The `invalid transaction limit per account` is configuration.

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

The dependency are:

* [Maven](https://maven.apache.org/install.html) (If want to build the code)
* [Java](https://www.java.com/en/) (JDK 8.0+)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This section describes how to run this module.


### Running the module

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

1. Download the zip file from: https://drive.google.com/file/d/1x7lSNmN7pSFFyvfIdnAY1UdT8bmQv9U9/view?usp=sharing
2. Unzip the zip file
   ```
   unzip grab-test.zip
   ```
3. cd to grab-test folder and make the build.sh executable
   ```
   cd grab-test
   ```
   ```
   chmod +x build.sh
   ```
4. Execute build.sh with "-b y" option to build and execute or "-b n" for directly running jar.
   ```
   sh build.sh -b y
   ```
   OR
   ```
   sh build.sh -b n
   ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

One sample data file is provided with the zip file and configured as default datafile in build.sh.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Nikhil Ashok Sharma - [@your_twitter](https://twitter.com/your_username) - email@example.com

Project Repo: [git@github.com:nikhilashoksharma/rule-based-fraud-detection.git)

<p align="right">(<a href="#top">back to top</a>)</p>