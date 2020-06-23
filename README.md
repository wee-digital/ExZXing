##Z-Coin for Android - Quickstart

To use my Base Structure, let extend BaseFragment

```
class ForgotPasswordVerifyFragment: BaseFragment() {
		//require
	    override val layoutId: Int get() = R.layout.fragment_verify_otp
	    private val viewModel by viewModel<ForgotPasswordVerifyViewModel>()
	    override fun getViewModel(): BaseViewModel = viewModel

	    override fun initUI(languageObj: JSONObject) {
	    	//TODO set dynamic text from languageJson
	    	//With Language.VERIFY_TITLE_TEXT is a KEY use to get text
	    	//Define KEY and language at Language.kt and language.json
	    	
	        tvTitle.text = languageObj.getString(Language.VERIFY_TITLE_TEXT)
	    }
	
	    override fun onViewReady(savedInstanceState: Bundle?) {
	    	//TODO something after view created
	    }
    }
```

When add new ViewModel, don't forget open MainModule.kt, add your Viewmodel here.

```
private val viewModelModule = module {
        viewModel { LoginViewModel(get(), get()) }
        viewModel { CountryViewModel(get(), get()) }
        viewModel { RegisterViewModel(get(), get()) }
		 .......
    }
```
  
