import React from "react";
import { useSelector } from "react-redux";
import FeedList from "../component/FeedList";
import UserFeed from "../component/UserFeed";
import UserList from "../component/UserList";

const HomePage = () => {
  const { isLoggedIn } = useSelector((store: any) => {
    return { isLoggedIn: store.isLoggedIn };
  });
  return (
    <div className="row">
      <div className="col-6 col-sm-8 col-md-8">
        {isLoggedIn && (
          <div>
            <UserFeed />
            <FeedList />
          </div>
        )}
      </div>
      <div className="col-6 col-sm-4 col-md--4">
        <div className="row">
          <UserList type={1}/>
        </div>
        <div className="row mt-1">
          <UserList type={0}/>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
