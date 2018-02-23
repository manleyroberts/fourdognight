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
    @IBOutlet weak var fromRegis: UILabel!
    @IBOutlet weak var emailField: UITextField!
    @IBOutlet weak var pwField: UITextField!
    @IBOutlet weak var emailLabel: UILabel!
    @IBOutlet weak var pwLabel: UILabel!
    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var errorLabel: UITextView!
    @IBOutlet weak var progressBar: UIProgressView!
    
    var loginTaskRunning: Bool = false
    
    //MARK: Actions
    @IBAction func loginAttempt(_ sender: Any) {
        if (!loginTaskRunning) {
            fromRegis.isHidden = true
            errorLabel.text = ""
            var error: String = ""
            let email: String = emailField.text!
            let pw: String = pwField.text!
            if (email == "") {
                error += "\"Email\" field is empty\n"
            }
            if (pw == "") {
                error += "\"Password\" field is empty\n"
            }
            if (error != "") {
                errorLabel.text = "Correct these errors:\n\(error)"
            } else {
                loginTaskRunning = true
                progressBar.setProgress(0, animated: false)
                showProgress(visibility: false)
                DispatchQueue.global(qos: .background).async {
                    // "attempt authentication"
                    DispatchQueue.main.async {
                        self.progressBar.setProgress(1.00, animated: true)
                    }
                    Thread.sleep(forTimeInterval: 1.00)
                    let loggedInUser: AbstractUser? = UserVerificationModel.attemptLogin(username: email, password: pw)
                    DispatchQueue.main.async {
                        self.loginTaskRunning = false
                        self.showProgress(visibility: true)
                        self.pwField.text = ""
                        if loggedInUser != nil  {
                            let mainViewController: MainViewController = UIStoryboard(name: "MainScreen", bundle: nil).instantiateViewController(withIdentifier: "main") as! MainViewController
                            self.present(mainViewController, animated: true, completion: nil)
                            mainViewController.passUser(user: loggedInUser!)
                        } else {
                            self.errorLabel.text = "Invalid username/ password"
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
    
    
    func cameFromRegistration() {
        fromRegis.isHidden = false
    }
    
    private func showProgress(visibility: Bool) {
        emailLabel.isHidden = !visibility
        emailField.isHidden = !visibility
        pwLabel.isHidden = !visibility
        pwField.isHidden = !visibility
        progressBar.isHidden = visibility
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
