import React from "react";
import "../styles/Home.css"; // CSS dosyasını ekliyoruz

// Görselleri import ediyoruz
import image1 from "../assets/1.png";
import image2 from "../assets/eye2.png";
import image3 from "../assets/12.png";
import image4 from "../assets/2.png";
import image5 from "../assets/13.jpeg";
import image6 from "../assets/logo.jpeg";
import image7 from "../assets/14.jpeg";
import image8 from "../assets/15.jpeg";
import image9 from "../assets/16.jpeg";
import image10 from "../assets/17.jpeg";
import image11 from "../assets/18.jpeg";


const Home = () => {
    return (
        <div className="wrapper">
            <div className="items">
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image1})`}}></div>
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image2})`}}></div>
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image3})`}}></div>
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image4})`}}></div>
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image5})`}}></div>
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image6})`}}></div>
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image7})`}}></div>
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image8})`}}></div>
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image9})`}}></div>
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image10})`}}></div>
                <div className="item" tabIndex="0" style={{backgroundImage: `url(${image11})`}}></div>

            </div>

        </div>

)
    ;
};

export default Home;
