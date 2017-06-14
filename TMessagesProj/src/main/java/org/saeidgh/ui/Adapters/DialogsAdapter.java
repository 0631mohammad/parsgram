/*
 * This is the source code of Telegram for Android v. 3.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2016.
 */

package org.saeidgh.ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.saeidgh.messenger.AndroidUtilities;
import org.saeidgh.messenger.MessagesController;
import org.saeidgh.messenger.support.widget.RecyclerView;
import org.saeidgh.tgnet.TLRPC;
import org.saeidgh.ui.Cells.DialogCell;
import org.saeidgh.ui.Cells.LoadingCell;

import java.util.ArrayList;

public class DialogsAdapter extends RecyclerView.Adapter {

    public static int dialogscategory;
    private Context mContext;
    private static int dialogsType;
    private long openedDialogId;
    private int currentCount;

    private class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }

    public DialogsAdapter(Context context, int type) {
        mContext = context;
        dialogsType = type;
    }

    public void setOpenedDialogId(long id) {
        openedDialogId = id;
    }

    public boolean isDataSetChanged() {
        int current = currentCount;
        return current != getItemCount() || current == 1;
    }

    public static ArrayList<TLRPC.Dialog> getDialogsArray() {
        if (dialogsType == 0) {
            if(dialogscategory==0) {
                return MessagesController.getInstance().dialogsBotOnly;
            }else if(dialogscategory==1) {
                return MessagesController.getInstance().dialogsChannelOnly;
            }else  if(dialogscategory==2) {
                return MessagesController.getInstance().dialogsJustGroupsOnly;
            }else if(dialogscategory==3) {
                return MessagesController.getInstance().dialogsContactOnly;
            }else  if(dialogscategory==4) {
                return MessagesController.getInstance().dialogsfaver;
            }else if(dialogscategory==5) {
                return MessagesController.getInstance().dialogs;
            }else  {
                return MessagesController.getInstance().dialogsunread;
            }
        } else if (dialogsType == 1) {
            return MessagesController.getInstance().dialogsServerOnly;
        } else if (dialogsType == 2) {
            return MessagesController.getInstance().dialogsGroupsOnly;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        int count = getDialogsArray().size();
        if (count == 0 && MessagesController.getInstance().loadingDialogs) {
            return 0;
        }
        if (!MessagesController.getInstance().dialogsEndReached) {
            count++;
        }
        currentCount = count;
        return count;
    }

    public TLRPC.Dialog getItem(int i) {
        ArrayList<TLRPC.Dialog> arrayList = getDialogsArray();
        if (i < 0 || i >= arrayList.size()) {
            return null;
        }
        return arrayList.get(i);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof DialogCell) {
            ((DialogCell) holder.itemView).checkCurrentDialogIndex();
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = new DialogCell(mContext);
        } else if (viewType == 1) {
            view = new LoadingCell(mContext);
        }
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder.getItemViewType() == 0) {
            DialogCell cell = (DialogCell) viewHolder.itemView;
            cell.useSeparator = (i != getItemCount() - 1);
            TLRPC.Dialog dialog = getItem(i);
            if (dialogsType == 0) {
                if (AndroidUtilities.isTablet()) {
                    cell.setDialogSelected(dialog.id == openedDialogId);
                }
            }
            cell.setDialog(dialog, i, dialogsType);
        }
    }

    @Override
    public int getItemViewType(int i) {
        if (i == getDialogsArray().size()) {
            return 1;
        }
        return 0;
    }
}