// unauthenticatedHostLogin.js
import React from "react";

function UnauthenticatedHostLogin() {
    return(
        <>
			<div className="register-complete-bg">
                <div className="register-complete-box">
                    <div className="register-complete-h2 register-unauthorized-header">Unable to login</div>
                    <div className="host-text">Your request to activate your account is still pending. You will be able to login when an admin activates your account.</div>
                    <a href="https://localhost:3000/home">
                        <button className="button home-unauthorised-host">
                            Homepage
                        </button>
                    </a>
                </div>
            </div>
        </>
    );
}

export default UnauthenticatedHostLogin;