import React from 'react';
import '../css/Header.css';
import logo from '../linktuitLogo.svg';

function Header() {
  return (
    <div className="header">
        <header>
            <img src={logo} alt="Linktuit logo"></img>
        </header>
    </div>
  );
}

export default Header;
