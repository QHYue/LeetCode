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
        //������������ò��������һ�����дӸ��ڵ㿪ʼ�洢�����Һ��ӽڵ�
        if(root==nullptr) return "[]";   //������Ϊ�շ���[]
        string result="[";    //����ַ���
        queue<TreeNode*> q;   //����
        q.push(root);
        while(!q.empty()){
            TreeNode* node=q.front();
            q.pop();    //ȡ�������׸��ڵ㲢��������
            if(node!=nullptr){
                result +=to_string(node->val) + ",";
                q.push(node->left);
                q.push(node->right);
            }
            else{
                result +="null,";
            }
        }
        result.erase(result.length()-1);   //ɾȥ����,
        result +="]";

        // cout<<result;
        return result;
    }

    // Decodes your encoded data to tree.
    TreeNode* deserialize(string data) {
        //���ַ����ָ��һ����Ԫ�أ�Ȼ�󹹽��µ����ڵ�
        if(data == "[]") return nullptr;
        vector<string> str;
        int mark=1;   //���ܷŵ�ѭ���ڣ�������ظ���ֵ
        string tmp="";
        for(int i=1;i<data.length()-1;i++){
            if(data[i]==','){
                tmp=data.substr(mark,i-mark);   //substr������start length��substring������start end
                str.push_back(tmp);
                mark=i+1;
            }
        }
        stringstream ss;
        ss<<str[0];
        int tmpi;
        ss>>tmpi;
        // cout<<"tmpi="<<tmpi<<endl;
        ss.clear();   //�����������ת������Ҫ������������Ӱ����һ������������
        TreeNode* root = new TreeNode(tmpi);   //���ڵ�
        queue<TreeNode* > q;
        q.push(root);
        int i=1;   //�����ָ����ַ�������
        while(!q.empty()){
            TreeNode* node=q.front();   //��ǰ�ڵ㣬������һֱ����root
            q.pop();
            if(i<str.size()&&str[i]!="null"){
                ss<<str[i];
                ss>>tmpi;
                ss.clear();
                node->left = new TreeNode(tmpi);
                q.push(node->left);
            }
            i++;
            if(i<str.size()&&str[i]!="null"){  //һֱ��������Խ��(DEADLYSINAL)
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
