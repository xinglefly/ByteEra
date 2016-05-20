package com.zhicai.byteera.activity.shouyi;


import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.shouyi.adapter.CompaniesAdapter;
import com.zhicai.byteera.activity.shouyi.entity.CompaniesIdList;
import com.zhicai.byteera.activity.shouyi.eventbus.CompaniesEvent;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.product.FinancingProduct;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.LoadMoreListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

/**
 * Created by chenzhenxing on 15/11/20.
 */
@SuppressWarnings("unused")
public class CompaniesListActivity extends BaseActivity {

    @Bind(R.id.head_title) HeadViewMain headTitle;
    @Bind(R.id.list_view) ListView mListView;
    private CompaniesAdapter mAdapter;
    private List<FinancingCompanyEntity> companyEntityList;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.companies_activity);
        ButterKnife.bind(this);
    }

    int lastItem;
    @Override
    protected void initData() {
        mAdapter = new CompaniesAdapter(this);
        mListView.setAdapter(mAdapter);
        getCrawlingCompanyList();
    }


    @OnItemClick(R.id.list_view)
    public void OnItemClick(AdapterView<?> parent,View view,int position){
        FinancingCompanyEntity companyEntity = (FinancingCompanyEntity) parent.getAdapter().getItem(position);
        Log.d("CompaniesListActivity", "companyEntity-->" + position + companyEntity.toString());
        operateDB(companyEntity);
    }

    private void operateDB(FinancingCompanyEntity companyEntity) {
        try {
            FinancingCompanyEntity selectCompany = db.findFirst(Selector.from(FinancingCompanyEntity.class).where("company_id", "=", companyEntity.getCompany_id()));
            CompaniesIdList company_id = db.findFirst(Selector.from(CompaniesIdList.class).where("company_id", "=", companyEntity.getCompany_id()));
            if (company_id!=null){
                FinancingCompanyEntity updateCompany = new FinancingCompanyEntity(selectCompany.getId(),selectCompany.getCompany_id(),false);
                db.update(updateCompany, "id", "company_id", "isChecked");
                db.delete(CompaniesIdList.class, WhereBuilder.b("company_id", "=", companyEntity.getCompany_id()));
                Log.d("db", "db FinancingCompanyEntity update->false" + companyEntity.getCompany_id());
                Log.d("db","db CompaniesIdList delete->"+companyEntity.getCompany_id());
            }else{
                FinancingCompanyEntity updateCompany = new FinancingCompanyEntity(selectCompany.getId(),selectCompany.getCompany_id(),true);
                db.update(updateCompany, "id", "company_id", "isChecked");
                db.save(new CompaniesIdList(companyEntity.getCompany_id(), companyEntity.getCompany_name()));
                Log.d("db", "db FinancingCompanyEntity update->true" + companyEntity.getCompany_id());
                Log.d("db", "db CompaniesIdList save->"+companyEntity.getCompany_id());
            }
            EventBus.getDefault().post(new CompaniesEvent(true));
            refreshData();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public void refreshData(){
        mAdapter.setData(getDBRefreshFinancing());

    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        headTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }


    public void getCrawlingCompanyList() {
        FinancingProduct.GetCrawlingCompanyListReq sec = FinancingProduct.GetCrawlingCompanyListReq.newBuilder().build();
        TiangongClient.instance().send("chronos", "get_crawling_company_list", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final FinancingProduct.GetCrawlingCompanyListResponse response = FinancingProduct.GetCrawlingCompanyListResponse.parseFrom(buffer);
                    Log.d("companiesListActivity","response-->");
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                try {
                                    if (db.tableIsExist(FinancingCompanyEntity.class)){
                                        companyEntityList = getDBRefreshFinancing();
                                        if (response.getCompaniesCount()!=companyEntityList.size()){
                                            db.deleteAll(FinancingCompanyEntity.class);
                                            companyEntityList = ModelParseUtil.CompanyEntityParse(response.getCompaniesList());
                                        }
                                        mAdapter.setData(companyEntityList);
                                    }else{
                                        companyEntityList = ModelParseUtil.CompanyEntityParse(response.getCompaniesList());
                                        mAdapter.setData(companyEntityList);
                                        Log.d("companiesListActivity", "request -->");
                                    }
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<FinancingCompanyEntity> getDBRefreshFinancing() {
        try {
            companyEntityList = db.findAll(FinancingCompanyEntity.class);
            Log.d("companiesListActivity", "use db FinancingCompanyEntity");
        } catch (DbException e) {
            e.printStackTrace();
        }
        return companyEntityList;
    }
}
