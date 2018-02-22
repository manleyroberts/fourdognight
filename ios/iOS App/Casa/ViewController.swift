//
//  ViewController.swift
//  Casa
//
//  Created by Isaac Weintraub on 2/9/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    //MARK: Actions
    
    @IBAction func startLoginScreen(_ sender: UIButton) {
        let loginViewController: UIViewController = UIStoryboard(name: "LoginScreen", bundle: nil).instantiateViewController(withIdentifier: "login")
        self.present(loginViewController, animated: true, completion: nil)
    }
    
    @IBAction func startRegisterScreen(_ sender: UIButton) {
        let registrationViewController: UIViewController = UIStoryboard(name: "RegistrationScreen", bundle: nil).instantiateViewController(withIdentifier: "registration")
        self.present(registrationViewController, animated: true , completion: nil)
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

