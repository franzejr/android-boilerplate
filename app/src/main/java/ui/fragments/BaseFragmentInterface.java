package ui.fragments;

public interface BaseFragmentInterface {

    public void onNetworkUnavailable();

    public void onServerUnreachable();

    public void onEmptyList();

    public void refresh();

    public void onPageNotFound();

    public void onGenericError();
}
