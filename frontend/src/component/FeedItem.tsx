import React from 'react';

const FeedItem = (props:any) => {

    const {feed} =props;

    return (
        <div className='card p-1'>
            {feed}
        </div>
    );
};

export default FeedItem;