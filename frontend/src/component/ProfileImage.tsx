import React from 'react';
import defaultImage from '../assets/profile.png'


const ProfileImage = (props:any) => {

    const {imageDb,width, tempimage,height} = props;
    let imgsrc = imageDb || defaultImage;
    if(imageDb)
    {
        imgsrc = "images/profile/"+ imageDb;
    }
    const image = tempimage || imgsrc;
    return (
        <img src={image} alt='profil' width={width} className="rounded-circle shadow m-auto" 
        height={height || width}
        onError={(event :any)=>{
            event.target.src = defaultImage;
        }}/>
    );
};

export default ProfileImage;