import { useSelector } from 'react-redux';
import Spinner from './Spinner';

const Loading = (props: any) => {

    const { isLoading } = useSelector((store:any)=>{
        return {
            isLoading:store.isApiPending
        }
    })
    return (
        <>
            {isLoading && <div className="d-flex justify-content-center">
                <Spinner/>
            </div>}
        </>

    );
};

export default Loading;