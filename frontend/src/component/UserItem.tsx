import React from 'react';
import { Link } from 'react-router-dom';
import ProfileImage from './ProfileImage';

const UserItem = (props: any) => {

    const { user } = props;
    let {image} = user;
    return (
        <div>
            <Link to={`/user/${user.username}`} className='list-group-item list-group-item-action'>
                <ProfileImage imageDb={image} width={32}/>
                <span className='ps-3'>{user.displayname + " - " + user.username}</span>
            </Link>

        </div>
    );
};

export default UserItem;