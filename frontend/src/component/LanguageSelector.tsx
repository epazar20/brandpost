import { useTranslation } from 'react-i18next';
import { changeBackendLanguage } from "../api/ApiCalls";


const LanguageSelector = (props: any) => {

    const {i18n} = useTranslation();
    const onChangeLanguage = (lang: string) => {
        i18n.changeLanguage(lang);
        changeBackendLanguage(lang);
    }

    return (
            <div>
                <img src="https://countryflagsapi.com/png/tr" alt="TR" width={25} height={20} onClick={() => { onChangeLanguage("tr") }} style={{ cursor: "pointer" }} />
                <img src="https://countryflagsapi.com/png/gb" alt="US" width={25} height={20} onClick={() => { onChangeLanguage("en") }} style={{ cursor: "pointer" }} />
            </div>
    );
};

export default LanguageSelector;