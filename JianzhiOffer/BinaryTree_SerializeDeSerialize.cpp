/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */


class Codec {
public:

    // Encodes a tree to a single string.
    string serialize(TreeNode* root) {
        //层序遍历：利用层序遍历，一个队列从根节点开始存储其左右孩子节点
        if(root==nullptr) return "[]";   //二叉树为空返回[]
        string result="[";    //结果字符串
        queue<TreeNode*> q;   //队列
        q.push(root);
        while(!q.empty()){
            TreeNode* node=q.front();
            q.pop();    //取队列中首个节点并弹出队列
            if(node!=nullptr){
                result +=to_string(node->val) + ",";
                q.push(node->left);
                q.push(node->right);
            }
            else{
                result +="null,";
            }
        }
        result.erase(result.length()-1);   //删去最后的,
        result +="]";

        // cout<<result;
        return result;
    }

    // Decodes your encoded data to tree.
    TreeNode* deserialize(string data) {
        //将字符串分割成一个个元素，然后构建新的树节点
        if(data == "[]") return nullptr;
        vector<string> str;
        int mark=1;   //不能放到循环内，否则会重复赋值
        string tmp="";
        for(int i=1;i<data.length()-1;i++){
            if(data[i]==','){
                tmp=data.substr(mark,i-mark);   //substr参数是start length；substring参数是start end
                str.push_back(tmp);
                mark=i+1;
            }
        }
        stringstream ss;
        ss<<str[0];
        int tmpi;
        ss>>tmpi;
        // cout<<"tmpi="<<tmpi<<endl;
        ss.clear();   //多次数据类型转换，需要清空流，否则会影响下一个的数据类型
        TreeNode* root = new TreeNode(tmpi);   //根节点
        queue<TreeNode* > q;
        q.push(root);
        int i=1;   //遍历分割后的字符串数组
        while(!q.empty()){
            TreeNode* node=q.front();   //当前节点，而不是一直都是root
            q.pop();
            if(i<str.size()&&str[i]!="null"){
                ss<<str[i];
                ss>>tmpi;
                ss.clear();
                node->left = new TreeNode(tmpi);
                q.push(node->left);
            }
            i++;
            if(i<str.size()&&str[i]!="null"){  //一直报错数组越界(DEADLYSINAL)
                ss<<str[i];
                ss>>tmpi;
                ss.clear();
                node->right = new TreeNode(tmpi);
                q.push(node->right);
            }
            i++;
        }
        return root;
    }
};

// Your Codec object will be instantiated and called as such:
// Codec codec;
// codec.deserialize(codec.serialize(root));
