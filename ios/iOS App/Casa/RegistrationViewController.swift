//
//  RegistrationViewController.swift
//  Casa
//
//  Created by Isaac Weintraub on 2/20/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

import UIKit
import Foundation

class RegistrationViewController: UIViewController {
    
    //MARK: Properties
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var emailLabel: UILabel!
    @IBOutlet weak var pwLabel: UILabel!
    @IBOutlet weak var pwLabel2: UILabel!
    @IBOutlet weak var nameField: UITextField!
    @IBOutlet weak var emailField: UITextField!
    @IBOutlet weak var pwField: UITextField!
    @IBOutlet weak var pwField2: UITextField!
    @IBOutlet weak var errorLabel: UITextView!
    @IBOutlet weak var progressBar: UIProgressView!
    @IBOutlet weak var successLabel: UILabel!
    @IBOutlet weak var adminLabel: UILabel!
    @IBOutlet weak var adminSwitch: UISwitch!
    
    var registrationTaskRunning: Bool = false
    let MIN_PASSWORD_LENGTH: Int = 8
    
    //MARK: Actions
    @IBAction func registrationAttempt(_ sender: Any) {
        if (!registrationTaskRunning) {
            successLabel.isHidden = true
            errorLabel.text = ""
            var error: String = ""
            let name: String = nameField.text!
            let email: String = emailField.text!
            let pw: String = pwField.text!
            let pw2: String = pwField.text!
            let admin: Bool = adminSwitch.isOn
            if (name == "") {
                error += "\"Name\" field is empty\n"
            }
            if (email == "") {
                error += "\"Email\" field is empty\n"
            } else if (!email.contains("@")) {
                error += "Invalid email \n"
            }
            if (pw == "") {
                error += "\"Password\" field is empty\n"
            }
            if (pw2 == "") {
                error += "\"Re-enter password\" field is empty\n"
            }
            if (pw != pw2) {
                error += "Passwords do not match\n"
            }
            if (pw.count < MIN_PASSWORD_LENGTH) {
                error += "Password must be at least 8 characters long\n"
            }
            if (error != "") {
                errorLabel.text = "Correct these errors:\n\(error)"
            } else {
                registrationTaskRunning = true
                progressBar.setProgress(0, animated: false)
                showProgress(visibility: false)
                DispatchQueue.global(qos: .background).async {
                    // "attempt authentication"
                    DispatchQueue.main.async {
                        self.progressBar.setProgress(1.00, animated: true)
                    }
                    Thread.sleep(forTimeInterval: 1.00)
                    let success: Bool = UserVerificationModel.attemptRegistration(name: name, username: email, password: pw, isAdmin: admin)
                    DispatchQueue.main.async {
                        self.registrationTaskRunning = false
                        self.showProgress(visibility: true)
                        self.pwField.text = ""
                        self.pwField2.text = ""
                        if (success) {
                            self.successLabel.isHidden = false
                            let loginViewController: LoginViewController = UIStoryboard(name: "LoginScreen", bundle: nil).instantiateViewController(withIdentifier: "login") as! LoginViewController
                            self.present(loginViewController, animated: true, completion: nil)
                            loginViewController.cameFromRegistration()
                        } else {
                            self.errorLabel.text = "Account already exists"
                        }
                    }
                }
            }
        }
    }
    @IBAction func goBack(_ sender: UIButton) {
        let previousViewController: UIViewController = UIStoryboard(name: "SplashScreen", bundle: nil).instantiateViewController(withIdentifier: "splash")
        self.present(previousViewController, animated: true, completion: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    private func showProgress(visibility: Bool) {
        nameLabel.isHidden = !visibility
        nameField.isHidden = !visibility
        emailLabel.isHidden = !visibility
        emailField.isHidden = !visibility
        pwLabel.isHidden = !visibility
        pwField.isHidden = !visibility
        pwLabel2.isHidden = !visibility
        pwField2.isHidden = !visibility
        progressBar.isHidden = visibility
        adminLabel.isHidden = !visibility
        adminSwitch.isHidden = !visibility
    }
}
