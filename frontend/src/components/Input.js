import React from 'react';
import '../css/Input.css';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

function Input() {

    // React hooks
    const [originalUrl, setOriginalURL] = React.useState("");
    const [shortURL, setShortURL] = React.useState("");
    const [msg, setMsg] = React.useState("");
    const [showMsg, setShowMsg] = React.useState(false);
    const [displayUrl, setDisplayUrl] = React.useState(false);
    const [copied, setCopy] = React.useState("");
    const [showCopy, setShowCopy] = React.useState(false);
    const copyRef = React.useRef(null);

    // event handler to update original URL
    const handleChange = e => {
      setOriginalURL(e.target.value);
      setShowMsg(false);
      setDisplayUrl(false);
      setShowCopy(false);
    }

    // sends original URL to backend in HTTP POST request
    // displays the response to user (shortened URL)
    async function sendURL(input) {
      fetch("/api/v1/shortenUrl", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(input)
      })
      .then(response => {
        return response.json();
      }) 
      .then(data => {
        return data.url;
      })
      .then(url => {
        setShortURL(url);
      })
      .catch(err => {
        setMsg("ERR");
      });
    }
  
    // handles info after form was submitted
    const handleSubmit = variables => {
      let createdDate = new Date();
      let numRequestsLeft = 10;

      if (originalUrl !== "") {
        const input = { originalUrl, createdDate, numRequestsLeft };
        sendURL(input)
        setMsg("OK");
        setDisplayUrl(true);
      }
      else {
        setMsg("ERR");
      }
      setShowMsg(true);
      variables.preventDefault();
    };

    // copies short link to clipboard
    function copy(e) {
      copyRef.current.select();
      document.execCommand('copy');
      e.target.focus();
      setCopy("Copied!");
      setShowCopy(true);
    };
  
    let url = "localhost:8080/api/v1/"+shortURL;

    return (
      <div className="input">
          <div className="form">
              <Form onSubmit={handleSubmit}>
                  <Form.Control type="text" placeholder="Example: https://www.this-url-is-too-long.com" onChange={handleChange} />
                  <Button variant="primary" type="submit">
                      Shorten <i className="fas fa-chevron-right"></i>
                  </Button>
              </Form>
          </div>
          <div className="status" >
            <div style={{display: showMsg ? 'block' : 'none' }}>
              {msg !== "ERR" ? <p>Success! Happy linking!  <i className="fas fa-smile" aria-hidden="true"></i></p> :
                              <p>Oops! Couldn't process your request. Please try again!  <i className="fas fa-frown" aria-hidden="true"></i></p>}
            </div>
          </div> 

          <div className="url" >
            <div style={{display: displayUrl ? 'block' : 'none' }}><p id = "clickId">Click to copy:</p></div>
            <input className="copyUrl animate__animated animate__bounceIn" style={{display: displayUrl ? 'block' : 'none' }} type="text" onClick={copy}
              ref={copyRef}
              value={url}
            />
            <div style={{display: showCopy ? 'block' : 'none' }}><p id = "copiedId">{copied}</p></div>
          </div>
          <div className="footer">
              <div className="icons">
                  <a href="https://github.com/rjafar"><i className="fab fa-github-square fa-2x"></i> </a>
                  <a href="https://www.linkedin.com/in/revanjafar/"><i className="fab fa-linkedin fa-2x" aria-hidden="true"></i> </a>
                  <a href="mailto:revanjafar@gmail.com"><i className="fas fa-envelope-square fa-2x"></i> </a>
              </div>
          </div>
      </div>
    );
    
  }

export default Input;
