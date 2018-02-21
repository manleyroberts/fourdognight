//
//  LoginViewController.swift
//  Casa
//
//  Created by Isaac Weintraub on 2/21/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {
    
    //MARK: Properties
    
    var loginTaskRunning: Bool = false
    
    //MARK: Actions
    @IBAction func loginAttempt(_ sender: Any) {
        if (!loginTaskRunning) {
            
        }
        
        /*
         registration attempt code:
         if (!registrationTaskRunning) {
             successLabel.isHidden = true
             errorLabel.text = ""
             var error: String = ""
             let name: String = nameField.text!
             if (name == "") {
                error += "\"Name\" field is empty\n"
             }
             let email: String = emailField.text!
             if (email == "") {
                error += "\"Email\" field is empty\n"
             } else if (!email.contains("@")) {
                error += "Invalid email \n"
             }
             let pw: String = pwField.text!
             let pw2: String = pwField.text!
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
                errorLabel.text = "Correct these errors:\n" + error
             } else {
                 registrationTaskRunning = true
                 progressBar.setProgress(0, animated: false)
                 showProgress(visibility: false)
                 DispatchQueue.global(qos: .background).async {
                     // "attempt authentication"
                     let max: Int = 10
                     for i in 1...max {
                         Thread.sleep(forTimeInterval: 0.200 / Double(max))
                         DispatchQueue.main.async {
                             self.progressBar.setProgress(Float(i) / Float(max), animated: true)
                         }
                    }
                    var success: Bool = true
                    if let _ = UserVerificationModel.user_list[email] {
                        success = false
                    } else {
                        UserVerificationModel.user_list[email] = pw
                    }
                    DispatchQueue.main.async {
                        self.registrationTaskRunning = false
                        self.showProgress(visibility: true)
                        self.pwField.text = ""
                        self.pwField2.text = ""
                        if (success) {
                            self.successLabel.isHidden = false
                        } else {
                            self.errorLabel.text = "Account already exists"
                        }
                    }
                }
             }
         }
         */
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
